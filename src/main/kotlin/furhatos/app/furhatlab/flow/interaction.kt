package furhatos.app.furhatlab.flow

import furhatos.app.furhatlab.nlu.*
import furhatos.flow.kotlin.*
import furhatos.gestures.*
import furhatos.nlu.*
import furhatos.nlu.common.*
import furhatos.skills.*
import furhatos.nlu.common.Yes

// Import audio control library
import java.io.File
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.Clip

////
// Audio control object
var audioClip: Clip? = null

fun playAudio(fileName: String) {
    val audioInputStream = AudioSystem.getAudioInputStream(File(fileName))
    audioClip = AudioSystem.getClip()
    audioClip?.open(audioInputStream)
    audioClip?.start()
}

fun stopAudio() {
    audioClip?.stop()
    audioClip?.close()
    audioClip = null
}

// The Skill starts here
val Start: State = state(Interaction) {
    onEntry {
        furhat.say("Hi there! Feeling stressed or tired? Let's take a moment to meditate together.")
        furhat.gesture(Gestures.Smile(duration = 2.0))
        furhat.say("You can choose from two types of meditation: Awakening or Relaxing.")
        furhat.ask("Which one would you like to try?")
    }

    onResponse<AwakeningMeditationIntent> {
        furhat.gesture(Gestures.BigSmile, async = true)
        furhat.say("Great choice! Awakening meditation will help energize your mind and body.")
        goto(StartAwakeningMeditationState)
    }

    onResponse<RelaxingMeditationIntent> {
        furhat.gesture(Gestures.BigSmile, async = true)
        furhat.say("Wonderful! Relaxing meditation will help you unwind and find calmness.")
        goto(StartRelaxingMeditationState)
    }

    onResponse {
        furhat.gesture(Gestures.ExpressSad, async = true)
        furhat.say("I'm sorry, I didn't understand that. You can say 'Awakening' or 'Relaxing' to choose.")
        reentry()
    }
}

// 1. Awakening Meditation State
val StartAwakeningMeditationState: State = state(Interaction) {
    onEntry {
        val filePath: String = javaClass.getResource("/sound/awakening-bkg-music.wav")?.path
            ?: throw IllegalArgumentException("Audio file not found")
        playAudio(filePath) // Start playing audio
        furhat.say("Let's begin your Awakening meditation.")
        furhat.gesture(Gestures.Smile, async = true)
        furhat.say("Sit comfortably, take a deep breath in through your nose... and out through your mouth.")
        delay(4000)
        goto(AwakeningBreathingExerciseState)
    }
}

val AwakeningBreathingExerciseState: State = state(Interaction) {
    onEntry {
        furhat.say("First, we'll energize your body with some deep breathing.")
        furhat.gesture(Gestures.BrowRaise, async = true)
        furhat.say("Breathe in deeply through your nose for five counts.")
        delay(5000)
        furhat.say("Hold for five counts.")
        delay(5000)
        furhat.say("Now exhale powerfully through your mouth for seven counts.")
        delay(7000)

        furhat.say("Let's repeat this three times to fully awaken your body.")
        repeat(2) {
            furhat.gesture(Gestures.BrowRaise, async = true)
            furhat.say("Breathe in...")
            delay(5000)
            furhat.say("Hold...")
            delay(5000)
            furhat.say("Exhale powerfully...")
            delay(7000)
        }

        furhat.gesture(Gestures.Wink, async = true)
        furhat.say("Well done! You should feel more energized already.")
        furhat.ask("Would you like to continue or should I repeat the session?")
    }

    onResponse<RepeatStepIntent> {
        furhat.say("Of course. Let me guide you through the breathing exercise again.")
        reentry()
    }

    onResponse<ContinueIntent> {
        furhat.gesture(Gestures.BigSmile, async = true)
        furhat.say("You're doing great. Let's move to the next step.")
        goto(AwakeningAffirmationState)
    }
}

val AwakeningAffirmationState: State = state(Interaction) {
    onEntry {
        furhat.say("Now, repeat these affirmations with me:")
        furhat.gesture(Gestures.BigSmile, async = true)
        furhat.say("'I am full of energy. I am ready to conquer the day.'")
        delay(3000)
        furhat.say("'Today is a fresh start. I embrace new opportunities.'")
        delay(3000)
        furhat.say("Take a moment to let these positive thoughts fill you.")
        delay(5000)
        furhat.ask("Would you like to continue or should I repeat the session?")
    }

    onResponse<RepeatStepIntent> {
        furhat.say("Let's repeat the affirmation:")
        reentry()
    }

    onResponse<ContinueIntent> {
        furhat.gesture(Gestures.BigSmile, async = true)
        furhat.say("We're almost wrapping up. You did an amazing job!")
        goto(EndMeditationState)
    }
}

// 2. Relaxing Meditation State
val StartRelaxingMeditationState: State = state(Interaction) {
    onEntry {
        val filePath: String = javaClass.getResource("/sound/relaxing-bkg-music.wav")?.path
            ?: throw IllegalArgumentException("Audio file not found")
        playAudio(filePath) // Start playing audio
        furhat.say("Let's begin your Relaxing meditation.")
        furhat.gesture(Gestures.Nod(duration = 2.0))
        furhat.say("Find a comfortable position, and take a deep breath in through your nose... and out through your mouth.")
        delay(4000)
        goto(RelaxingBreathingExerciseState)
    }
}

val RelaxingBreathingExerciseState: State = state(Interaction) {
    onEntry {
        furhat.say("We'll start with a gentle breathing exercise to calm your body.")
        furhat.gesture(Gestures.Thoughtful, async = true)
        furhat.say("Breathe in deeply through your nose for four counts.")
        delay(4000)
        furhat.say("Hold it gently for four counts.")
        delay(4000)
        furhat.say("Now exhale slowly and softly through your mouth for six counts.")
        delay(6000)

        furhat.say("We'll repeat this three times to fully relax your body.")
        repeat(2) {
            furhat.gesture(Gestures.Thoughtful, async = true)
            furhat.say("Breathe in...")
            delay(4000)
            furhat.say("Hold gently...")
            delay(4000)
            furhat.say("Exhale slowly...")
            delay(6000)
        }

        furhat.gesture(Gestures.BigSmile, async = true)
        furhat.say("Great job. Your body is now in a relaxed state.")
        furhat.ask("Would you like to continue or should I repeat the session?")
    }

    onResponse<RepeatStepIntent> {
        furhat.say("Of course. Let me guide you through the breathing exercise again.")
        reentry()
    }

    onResponse<ContinueIntent> {
        furhat.gesture(Gestures.BigSmile, async = true)
        furhat.say("You're doing amazing. Let's move to the next step.")
        goto(RelaxingVisualizationState)
    }


}

val RelaxingVisualizationState: State = state(Interaction) {
    onEntry {
        furhat.say("Close your eyes and imagine a peaceful scene.")
        furhat.gesture(Gestures.CloseEyes)
        furhat.say("Visualize a quiet beach with gentle waves lapping at the shore.")
        delay(5000)
        furhat.say("Feel the warmth of the sun on your skin and the soft breeze in your hair.")
        delay(5000)
        furhat.say("Let this calmness surround you completely.")
        delay(5000)
        furhat.say("Take a deep breath and slowly open your eyes when you're ready.")
        furhat.gesture(Gestures.OpenEyes)
        furhat.ask("Would you like to continue or should I repeat the session?")
    }

    onResponse<RepeatStepIntent> {
        furhat.say("Let's repeat the visualization:")
        reentry()
    }
    onResponse<ContinueIntent> {
        furhat.gesture(Gestures.BigSmile, async = true)
        furhat.say("We're almost done here. You’ve done a fantastic job!")
        goto(EndMeditationState)
    }
}

// 3. End Meditation State
val EndMeditationState : State = state(Interaction) {
    onEntry {
        //furhat.say("Meditation complete. How do you feel?")
        stopAudio() // Stop playing audio
        furhat.ask("Would you like to try another meditation, or are you done for now?")
    }

    onResponse<AwakeningMeditationIntent> {
        furhat.say("Let's begin the Awakening meditation again.")
        goto(StartAwakeningMeditationState)
    }

    onResponse<RelaxingMeditationIntent> {
        furhat.say("Let's begin the Relaxing meditation again.")
        goto(StartRelaxingMeditationState)
    }

    onResponse<EndSessionIntent> {
        furhat.say("I'm glad I could help you today. Have a wonderful day!")
        furhat.gesture(Gestures.Smile)
        goto(Idle)
    }

    onResponse {
        furhat.gesture(Gestures.ExpressSad, async = true)
        furhat.say("I'm sorry, I didn't understand that. You can choose Awakening, Relaxing, or say you're done.")
        reentry()
    }
}

//fun assistance() : State = state(Interaction) {
//    onEntry {
//        furhat.ledStrip.solid(java.awt.Color(0,0,0))
//        furhat.gesture(Gestures.BigSmile)
//        furhat.say("How can I help you today ?")
//        furhat.ledStrip.solid(java.awt.Color.GREEN)
//        furhat.gesture(Gestures.Smile)
//        furhat.listen()
//
//    }
//
//
//    onUserLeave (instant = false) {
//        furhat.ledStrip.solid(java.awt.Color(0,0,0))
//        furhat.ledStrip.solid(java.awt.Color.YELLOW)
//        furhat.ledStrip.solid(java.awt.Color(0,0,0))
//        furhat.say("Bye for now!")
//    }
//
//    onResponse<Help> {
//        furhat.ledStrip.solid(java.awt.Color(0,0,0))
//        furhat.gesture(Gestures.BigSmile)
//        furhat.say(" I am sending a notification for someone who can help you! please have a seat! they will be with you in few minutes!")
//    }
//
//
//    onResponse <Meeting> {
//        furhat.ledStrip.solid(java.awt.Color(0,0,0))
//        furhat.say("You need to check in prior to your meeting! You can use the kiosk to check in, Just enter your email address, or the reference code if you have one!")
//        furhat.gesture(Gestures.BigSmile)
//    }
//    onResponse <Work> {
//        furhat.ledStrip.solid(java.awt.Color(0,0,0))
//        furhat.say("I am always happy to see you! As you know You need to check in prior to entering the robotarium! Just select the first button and enter your details!")
//        furhat.gesture(Gestures.BigSmile)
//    }
//    onResponse <Cafe> {
//        furhat.ledStrip.solid(java.awt.Color(0,0,0))
//        furhat.say("There is a coffee machine in the first floor, or you can walk to the cafe in the university building across the road.")
//        furhat.gesture(Gestures.BigSmile)
//    }
//    onResponse <Notsure> {
//        furhat.ledStrip.solid(java.awt.Color(0,0,0))
//        furhat.say("I am here if you need my help!")
//
//    }
//    onResponse<Wifi> {
//        furhat.ledStrip.solid(java.awt.Color(0,0,0))
//        furhat.gesture(Gestures.BigSmile)
//        furhat.say("You can connect to our guest wifi called the national robotarium wifi and the password is N R 2 0 2 1")
//    }
//    onResponse<Delivery> {
//        furhat.ledStrip.solid(java.awt.Color(0,0,0))
//        furhat.say("If you have a delivery please drop it next to me. I am sending a notification to someone who can come to pick it up.")
//    }
//    onResponse<Howareyou> {
//        furhat.ledStrip.solid(java.awt.Color(0,0,0))
//        furhat.gesture(Gestures.BigSmile)
//        furhat.say("Fortunately! I was programmed to always feel good! ")
//        furhat.gesture(Gestures.BigSmile)
//    }
//
//    onResponse<TalkAboutRobotarium> {
//        furhat.ledStrip.solid(java.awt.Color(0,0,0))
//        furhat.say("the National Robotarium is exploring collaborative interaction between humans, robots and their environments, translating cutting-edge research into new technologies.")
//        furhat.gesture(Gestures.BigSmile)
//    }
//    onResponse<Thanks> {
//        furhat.ledStrip.solid(java.awt.Color(0,0,0))
//        furhat.say("You are Welcome!")
//        furhat.gesture(Gestures.BigSmile)
//    }
//
//    onResponse { // Catches everything else
//
//        furhat.ledStrip.solid(java.awt.Color(0,0,0))
//        furhat.say({
//            random {
//                +" Sorry! I didn't hear you, Would you speak a little louder when my green light is on please"
//                +" Sorry I didn't catch that! Can you speak up, whenever my green light is on please?"
//                +"Can you please speak more loudly, when the green light is on"
//                +" Is it possible for you to raise your voice, a little bit please! When my green light is on! Just so I can hear you clearly!"
//
//            }
//        })
//        furhat.ledStrip.solid(java.awt.Color.GREEN)
//        furhat.gesture(Gestures.Smile)
//        furhat.listen()
//
//    }
//    onNoResponse { // Catches silence
//        furhat.ledStrip.solid(java.awt.Color(0, 0, 0))
//        furhat.say({
//            random {
//                +" Sorry! I didn't hear you, Would you speak a little louder when my green light is on please"
//                +" Sorry I didn't catch that! Can you speak up, whenever my green light is on please?"
//                +"Can you please speak more loudly, when the green light is on"
//                +" Is it possible for you to raise your voice, a little bit please! When my green light is on! Just so I can hear you clearly!"
//
//            }
//        })
//    }
//}

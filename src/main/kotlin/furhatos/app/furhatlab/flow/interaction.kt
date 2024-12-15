package furhatos.app.furhatlab.flow

import furhatos.app.furhatlab.nlu.*
import furhatos.flow.kotlin.*
import furhatos.gestures.*
import furhatos.nlu.*
import furhatos.nlu.common.*
import furhatos.skills.*
import furhatos.nlu.common.Yes

// The Skill starts here
val Start : State = state(Interaction) {
// When a new user enters the scene
    onEntry {
        furhat.say("Hi there! Feeling stressed? Let's take a moment to relax together.")
        furhat.say("Find a quiet spot and let's begin.")
        furhat.say("Right now we only have: Morning Meditation.")
        goto(StartMeditationState)
    }
}

val StartMeditationState : State = state(Interaction) {
    onEntry {
        furhat.say("Good morning! Let's start your day with a refreshing meditation.")
        furhat.say("Sit comfortably and take a deep breath in... and out. Let's begin.")
        goto(BreathingExerciseState)
    }
}

val BreathingExerciseState : State = state(Interaction) {
    onEntry {
        furhat.say("Let's do a breathing exercise.")
        furhat.say("Breathe in deeply through your nose for four counts...")
        delay(4000) // 4 seconds
        furhat.say("Hold it for four counts...")
        delay(4000)
        furhat.say("Now exhale slowly through your mouth for six counts.")
        delay(6000)

        repeat(2) {
            furhat.say("Let's repeat.")
            furhat.say("Breathe in...")
            delay(4000)
            furhat.say("Hold...")
            delay(4000)
            furhat.say("Exhale...")
            delay(6000)
        }

        goto(PositiveAffirmationState)
    }

    onResponse<RepeatStepIntent> {
        furhat.say("Of course. Let me guide you through the breathing exercise again.")
        reentry()
    }

    onResponse<SlowDownIntent> {
        furhat.say("Sure, I'll go slower. We'll take each breath carefully.")
        delay(2000)
        reentry()
    }
}

val PositiveAffirmationState : State = state(Interaction) {
    onEntry {
        furhat.say("Now, repeat after me:")
        furhat.say("'Today is a fresh start. I am ready for new opportunities.'")
        goto(VisualizationState)
    }

    onResponse<RepeatStepIntent> {
        furhat.say("Let's repeat the affirmation:")
        furhat.say("'Today is a fresh start. I am ready for new opportunities.'")
        reentry()
    }
}

val VisualizationState : State = state(Interaction) {
    onEntry {
        furhat.say("Close your eyes and imagine a warm light surrounding you.")
        furhat.say("This light fills you with energy and positivity for the day ahead.")
        furhat.say("Meditation complete. How do you feel?")
        goto(EndMeditationState)
    }
}

val EndMeditationState : State = state(Interaction) {
    onResponse<ChangeMeditationIntent> {
        furhat.say("No problem. Let's reset and begin a relaxation meditation.")
        furhat.say("Take a deep breath in and out as we start.")
        goto(StartMeditationState)
    }

    onResponse<RepeatStepIntent> {
        furhat.say("Which part of the meditation would you like me to repeat?")
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

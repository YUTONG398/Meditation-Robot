package furhatos.app.furhatlab.flow

import furhatos.flow.kotlin.*
import furhatos.flow.kotlin.voice.PollyVoice
import furhatos.skills.SimpleEngagementPolicy
import furhatos.skills.SingleUserEngagementPolicy

val Init: State = state {
    init {
        val defaultVoice = PollyVoice("Emma")
        furhat.setVoice(defaultVoice)
        furhat.setCharacter("Isabel")
        users.setSimpleEngagementPolicy(3.0, 3)

        if (users.count > 0) {
            furhat.attend(users.random)
            goto(Start)
        }
    }

    onEntry {
        furhat.attendNobody()
    }

    onUserEnter {
        furhat.attend(it)
        goto(Start)
    }
}

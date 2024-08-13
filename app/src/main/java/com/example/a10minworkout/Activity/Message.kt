package com.example.a10minworkout.Activity


class Message(var message: String, var sentBy: String) {

    companion object {
        var SENT_BY_ME = "me"
        var SENT_BY_BOT = "bot"
    }
}





//class Message {
//
//
//    var SENT_BY_ME = "me"
//    var SENT_BY_BOT = "bot"
//
//    var message: String? = null
//    var sentBy: String? = null
//
//    fun getMessage(): String? {
//        return message
//    }
//
//    fun setMessage(message: String?) {
//        this.message = message
//    }
//
//    fun getSentBy(): String? {
//        return sentBy
//    }
//
//    fun setSentBy(sentBy: String?) {
//        this.sentBy = sentBy
//    }
//
//    fun Message(message: String?, sentBy: String?) {
//        this.message = message
//        this.sentBy = sentBy
//    }
//}
//
//
//class Message(var message: String, var sentBy: String) {
//
//    companion object {
//        var SENT_BY_ME = "me"
//        var SENT_BY_BOT = "bot"
//    }
//}
package com.example.slackr

class GroupPost {

    val pId: String
    val pTitle: String
    val pDesc: String
    val pTime: String
    val userId: String
    val userName: String
    val userEmail: String

    //Might add user pic later

    constructor(pId: String, pTitle: String, pDesc: String, pTime: String,
                userId: String, userName: String, userEmail: String) {
        this.pId = pId
        this.pTitle = pTitle
        this.pDesc = pDesc
        this.pTime = pTime
        this.userId = userId
        this.userName = userName
        this.userEmail = userEmail
    }



}
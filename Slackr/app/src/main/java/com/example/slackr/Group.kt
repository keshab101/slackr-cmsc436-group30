package com.example.slackr

import com.google.firebase.database.DatabaseReference


/** Each group will have the following -
 * groupID: the ID of the group
 * groupName: the name of the group
 * groupMembers: number of members in the group
 * membersIDS: IDS of all the members that are in the group
 * posts: a list of GroupPosts (initially empty)
 * **/

class Group {

    var groupId: String = ""
    var groupName: String = ""
    var groupMembers: String = ""

    //Might add user pic later

    constructor(groupId: String, groupName: String, groupMembers: String) {
        this.groupId = groupId
        this.groupName = groupName
        this.groupMembers = groupMembers
    }

//    constructor(groupId: String, groupName: String, groupMembers: String,
//                memberIDS: DatabaseReference, posts: DatabaseReference){
//        this.groupId = groupId
//        this.groupName = groupName
//        this.groupMembers = groupMembers
//        this.memberIDS = memberIDS
//        this.posts = posts
//    }

    override fun toString(): String {
        return "GroupId: $groupId, Groupname: $groupName, GroupMembers: $groupMembers"
    }

}


package com.example.slackr.fragments

import com.google.firebase.database.DatabaseReference
import javax.security.auth.Subject


/** Each group will have the following -
 * groupID: the ID of the group
 * groupName: the name of the group
 * groupMembers: number of members in the group
 * memberIDs: IDs of all the members that are in the group
 * posts: a list of GroupPosts (initially empty)
 * **/

class Group {

    var groupId: String = ""
    var groupName: String = ""
    var groupMembers: String = ""
    var members: HashMap<String, String> = HashMap()
    var groupLocation: String = ""
    var groupDescription: String = ""
    var groupSubject: String = ""

    //Might add user pic later

    constructor(groupId: String, groupName: String, groupMembers: String) {
        this.groupId = groupId
        this.groupName = groupName
        this.groupMembers = groupMembers
    }

    constructor(groupId: String, groupName: String, groupMembers: String,
                members: HashMap<String, String>, groupLocation: String, groupDescription: String,
                groupSubject: String){
        this.groupId = groupId
        this.groupName = groupName
        this.groupMembers = groupMembers
        this.members = members
        this.groupLocation = groupLocation
        this.groupDescription = groupDescription
        this.groupSubject = groupSubject
    }

    override fun toString(): String {
        return "GroupId: $groupId, Groupname: $groupName, GroupMembers: $groupMembers"
    }

}


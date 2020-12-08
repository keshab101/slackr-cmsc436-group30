package com.example.slackr.searchGroupTypes

class StudyHabit {

    var studyDay: String? = null
    var studyTime: String? = null
    var studyType: String? = null
    var studyMethod: String? = null
    var studySubject: String? = null

    constructor(studyDay: String, studyTime: String, studyType: String, studyMethod: String, studySubject: String){
        this.studyDay = studyDay
        this.studyTime = studyTime
        this.studyType = studyType
        this.studyMethod = studyMethod
        this.studySubject = studySubject
    }



}
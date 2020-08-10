package com.hoangpro.assignmentandroidnetworking.model

class EventMessage(any: Any,state:EventState) {
    val any=any
    val state=state

    enum class EventState{
        EVENT_UPDATE_PROGRESS_DOWNLOAD
    }
}
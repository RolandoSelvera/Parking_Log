package com.rolandoselvera.parkinglog.data.models.results

import com.rolandoselvera.parkinglog.data.models.enums.RegisterStatus

data class ResultError(
    var status: RegisterStatus?,
    var message: String?
)
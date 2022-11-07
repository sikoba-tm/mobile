package com.tekmob.sikoba.data.remote.response

import com.google.gson.annotations.SerializedName
import com.tekmob.sikoba.model.Bencana

data class ResponseListBencana(

	@field:SerializedName("ResponseListBencana")
	val response: List<Bencana?>? = null
)

package com.jarroyo.graphqlexample.data.remote

abstract class NetworkSystem {
    abstract fun isNetworkAvailable(): Boolean
}
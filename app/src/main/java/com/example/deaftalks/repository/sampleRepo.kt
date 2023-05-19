object sampleRepo {
    const val TAG="SampleRepo"
    private var instance: sampleRepo? = null

    fun getInstance(): sampleRepo {
        if (instance == null) {
            instance = sampleRepo
        }
        return instance!!
    }


}

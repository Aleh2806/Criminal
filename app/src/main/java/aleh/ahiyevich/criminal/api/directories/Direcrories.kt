package aleh.ahiyevich.criminal.api.directories

data class Data(
    val data: Directories,
)

data class Directories(
    val directories: List<Directory>
)

data class Directory(
    val directory: DirectoryDescription
)

data class DirectoryDescription(
    val name: String,
    val image: String
)
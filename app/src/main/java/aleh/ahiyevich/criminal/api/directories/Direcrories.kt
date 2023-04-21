package aleh.ahiyevich.criminal.api.directories

data class Data(
    val data: Directories
)

data class Directories(
    val directories: List<Directory>,
    val documents: List<Document>
)

data class Directory(
    val directory: DirectoryDescription,
    val documents: List<Document>
)

data class DirectoryDescription(
    val name: String,
    val image: String
)

data class Document(
    val document: DocumentDescription
)

data class DocumentDescription(
    val name: String,
    val description: String,
)
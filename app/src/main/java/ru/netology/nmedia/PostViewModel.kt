package ru.netology.nmedia

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.db.AppDb
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositorySQLiteImpl

private val empty = Post(
    id = 0,
    content = "",
    author = "",
    likedByMe = false,
    likes = 0,
    shares = 0,
    views = 0,
    published = "",
    videoUrl = ""
)

class PostViewModel (application: Application): AndroidViewModel(application) {
    private val repository: PostRepository = PostRepositorySQLiteImpl.PostRepositorySQLiteImpl(
        AppDb.getInstance(application).postDao
    )
    private val edited = MutableLiveData(empty)

    val data = repository.getAll()
    fun likeById(id: Long) = repository.likeById(id)
    fun shared(id: Long) = repository.shared(id)
    fun viewed(id: Long) = repository.viewed(id)
    fun removeById(id: Long) = repository.removeById(id)
    fun video (videoUrl: String) = repository.video(videoUrl)

    fun save() {
        edited.value?.let {
            repository.save(it)
        }
        edited.value = empty
    }

    fun edit(post: Post) {
        edited.value = post
    }

    fun changeContent(content: String) {
        val text = content.trim()
        if (edited.value?.content == text) {
            return
        }
        edited.value = edited.value?.copy(content = text)
    }
}
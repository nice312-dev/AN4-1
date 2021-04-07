package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import ru.netology.nmedia.Post

interface PostRepository {
    fun getAll(): LiveData<List<Post>>
    fun likeById(id: Long)
    fun shared(id: Long)
    fun viewed(id: Long)
    fun removeById(id: Long)
    fun save(post: Post)
    fun video (videoUrl: String)
}
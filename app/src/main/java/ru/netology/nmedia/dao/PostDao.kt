package ru.netology.nmedia.dao

import ru.netology.nmedia.Post

interface PostDao {
    fun getAll(): List<Post>
    fun likeById(id: Long)
    fun shared(id: Long)
    fun viewed(id: Long)
    fun removeById(id: Long)
    fun save(post: Post): Post
    fun video (videoUrl: String)
}
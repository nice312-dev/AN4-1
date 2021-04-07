package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post
import ru.netology.nmedia.dao.PostDao

class PostRepositorySQLiteImpl {
    class PostRepositorySQLiteImpl(
        private val dao: PostDao
    ) : PostRepository {
        private var posts = emptyList<Post>()
        private val data = MutableLiveData(posts)

        init {
            posts = dao.getAll()
            data.value = posts
        }

        override fun getAll(): LiveData<List<Post>> = data

        override fun save(post: Post) {
            val id = post.id
            val saved = dao.save(post)
            posts = if (id == 0L) {
                listOf(saved) + posts
            } else {
                posts.map {
                    if (it.id != id) it else
                       it.copy(content = post.content)
                }
            }
            data.value = posts

        }

        override fun video(videoUrl: String) {
            dao.video(videoUrl)
        }

        override fun likeById(id: Long) {
            dao.likeById(id)
            posts = posts.map {
                if (it.id != id) it else it.copy(
                    likedByMe = !it.likedByMe,
                    likes = if (it.likedByMe) it.likes - 1 else it.likes + 1
                )
            }
            data.value = posts
        }

        override fun shared(id: Long) {
            dao.shared(id)
            posts = posts.map {
                if (it.id != id) it else it.copy(shares = it.shares + 1)
            }
            data.value = posts
        }

        override fun viewed(id: Long) {
            dao.viewed(id)
            posts = posts.map {
                if (it.id != id) it else it.copy(views = it.views + 1)
            }
            data.value = posts
        }

        override fun removeById(id: Long) {
            dao.removeById(id)
            posts = posts.filter { it.id != id }
            data.value = posts
        }
    }
}
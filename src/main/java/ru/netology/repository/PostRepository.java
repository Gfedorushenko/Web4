package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PostRepository {

  private final AtomicLong id=new AtomicLong(1);
  private final ConcurrentHashMap<Long, Post> requests = new ConcurrentHashMap<>();
  public List<Post> all() {
    return new ArrayList<>(requests.values());
  }

  public Optional<Post> getById(long id) {return Optional.ofNullable(requests.get(id));}

  public Post save(Post post) {
    long postId=post.getId();
    if(postId==0) {
      postId = id.getAndIncrement();
      post.setId(postId);
    }
    requests.put(postId, post);
    return post;
  }
  public void removeById(long id) { requests.remove(id);  }
}

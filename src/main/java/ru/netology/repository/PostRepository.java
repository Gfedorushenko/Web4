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
    List<Post> list = new ArrayList<>();
    for (Post post : requests.values()) {
      if (!post.isRemoved()) {
        list.add(post);
      }
    }
    return new ArrayList<>(list);
  }
  public Optional<Post> getById(long id) {
    if(requests.containsKey(id) && !requests.get(id).isRemoved())
      return Optional.of(requests.get(id));
    else
      return Optional.empty();
  }
  public Optional<Post> save(Post post) {
    long postId=post.getId();
    if(postId==0) {
      postId = id.getAndIncrement();
      post.setId(postId);
    }
    if(requests.containsKey(postId)) {
      if (!requests.get(postId).isRemoved())
        return Optional.empty();
      else {
        requests.put(postId, post);
        return Optional.of(post);
      }
    }else{
      requests.put(postId, post);
      return Optional.of(post);
    }
  }
  public void removeById(long id) {
    requests.put(id,requests.get(id).setRemoved(true));
  }
}

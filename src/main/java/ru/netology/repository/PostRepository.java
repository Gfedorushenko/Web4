package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentSkipListMap;
@Repository
public class PostRepository {

  private static final ConcurrentSkipListMap<Long, Post> requests = new ConcurrentSkipListMap<>();
  public List<Post> all() {
    List<Post> list = new ArrayList<>();
    for(Post post : requests.values()){
      list.add(post);
    }
    return list;
  }

  public Optional<Post> getById(long id) {return Optional.ofNullable(requests.get(id));}

  public Post save(Post post) {
    long count=post.getId();
    if(count==0)
      count=requests.lastKey()+1;
    post.setId(count);
    requests.put(count, post);
    return post;
  }
  public void removeById(long id) { requests.remove(id);  }
}

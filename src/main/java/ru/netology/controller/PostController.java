package ru.netology.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;
import ru.netology.service.PostService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;
@Controller
@RequestMapping("/api/posts")
public class PostController {
    public static final String APPLICATION_JSON = "application/json";
    private final PostService service;
    private static final Gson gson = new Gson();

    public PostController(PostService service) {
        this.service = service;
    }

    @GetMapping
    public void all(HttpServletResponse response) throws IOException {
        final var data = service.all();
        System.out.println(data);
        Type listType = new TypeToken<List<Post>>() {
        }.getType();
        response.setContentType(APPLICATION_JSON);
        response.getWriter().print(gson.toJson(data, listType));
    }
    @GetMapping("/{id}")
    public void getById(@PathVariable long id, HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        final var data=service.getById(id);
        if(data.isEmpty()) {
            response.setStatus(404);
            response.getWriter().print("id: " + id + " not found");
        }
        else
            response.getWriter().print(gson.toJson(data));

    }
    @PostMapping
    public void save(Reader body, HttpServletResponse response) throws IOException {
        final var post = gson.fromJson(body, Post.class);
        final var data = service.save(post);
        response.setContentType(APPLICATION_JSON);
        response.getWriter().print(gson.toJson(data));
    }

    @DeleteMapping("/{id}")
    public void removeById(@PathVariable long id, HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        response.getWriter().print("id: " + id + " removed");
    }
}

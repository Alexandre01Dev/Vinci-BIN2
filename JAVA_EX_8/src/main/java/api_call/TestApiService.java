package api_call;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;

public class TestApiService {
    private static ApiService apiService = new ApiService();
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) {
        printAllPosts();
        printAllPostsWithCount();
        dealWithUnexistedApi();
        printAllPostsWithUserAndComments();
        printAllPostsWithUserAndCommentsAnd2Threads(); // Challenge optionnel
    }


    public static void printAllPosts() {
        System.out.println("1. Imprimer tous les posts");
        long start = System.currentTimeMillis();

        // TODO 1 : Affichez tous les posts dans le terminal (utiliser la méthode fetchPosts de ApiService)

        apiService.fetchPosts().whenComplete((response, error) -> {
            System.out.println("Je fetch hein");
            if (error != null) {
                System.out.println("Error: " + error.getMessage());
            } else {
                try {
                    JsonNode jsonNode = objectMapper.readTree(response);
                    System.out.println(jsonNode);
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }).join();

        long end = System.currentTimeMillis();
        System.out.println("1. Total execution time: " + (end - start) + " ms");

    }

    public static void printAllPostsWithCount() {
        System.out.println("2. Imprimer tous les posts suivi du nombre de posts");
        long start = System.currentTimeMillis();


        // TODO 2 : Affichez tous les posts, retourner les posts sous forme d'un JsonNode
        //  puis, afficher le nombre de posts
        apiService.fetchPosts().whenComplete((response, error) -> {
            if (error != null) {
                System.out.println("Error: " + error.getMessage());
            } else {
                try {
                    JsonNode jsonNode = objectMapper.readTree(response);
                    System.out.println(jsonNode);
                    System.out.println("Number of posts: " + jsonNode.size());
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }).join();


        long end = System.currentTimeMillis();
        System.out.println("2. Total execution time: " + (end - start) + " ms");

    }

    public static void dealWithUnexistedApi() {
        System.out.println("3. Gérer une erreur lorsqu'une API n'existe pas");
        long start = System.currentTimeMillis();

        // TODO 3 : Tentez d'afficher le résultat d'une API qui n'existe pas
        //  en utilisant la méthode fetchData de ApiService.
        //  Gérez l'exception en affichant le code d'erreur retourné
        //  par la méthode fetchData.

        try {
            apiService.fetchData("https://kd.typicode.co").whenComplete((response, error) -> {
                    try {
                        JsonNode jsonNode = objectMapper.readTree(response);
                        System.out.println(jsonNode);
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
            }).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();
        System.out.println("3. Total execution time: " + (end - start) + " ms");
    }

    public static void printAllPostsWithUserAndComments() {
        System.out.println("4. Imprimer tous les posts avec les commentaires et les détails de l'utilisateur");
        long start = System.currentTimeMillis();

        // TODO 4 : Affichez tous les posts, les commentaires et les détails de l'utilisateur sous un format du
        //  genre : "Post (postId:1) : {post details}
        //           Comments: (postId:1) : [{comments details}]
        //           User: (postId:1) : {user details}"
        //  Pour chaque "post", vous devez lancer en parallèle toute les requêtes pour
        //  récupérer les commentaires et les détails de l'utilisateur. De plus, vous devez faire attention à
        //  attendre que tant les commentaires que les détails de l'utilisateur soient récupérés avant d'afficher
        //  toutes les infos pour un post donné.

        apiService.fetchPosts().whenComplete((response, error) -> {
            if (error != null) {
                System.out.println("Error: " + error.getMessage());
            } else {
                try {
                    JsonNode jsonNode = objectMapper.readTree(response);
                    IntStream.range(0, jsonNode.size()).forEach(i -> {
                        JsonNode post = jsonNode.get(i);
                        int postId = post.get("id").asInt();
                        CompletableFuture<String> comments = apiService.fetchCommentsForPost(postId);
                        CompletableFuture<String> user = apiService.fetchUser(post.get("userId").asInt());
                        comments.thenCombine(user, (commentsResponse, userResponse) -> {
                            try {
                                JsonNode commentsNode = objectMapper.readTree(commentsResponse);
                                JsonNode userNode = objectMapper.readTree(userResponse);
                                System.out.println("Post (postId:" + postId + ") : " + post);
                                System.out.println("Comments: (postId:" + postId + ") : " + commentsNode);
                                System.out.println("User: (postId:" + postId + ") : " + userNode);
                            } catch (Exception e) {
                                System.out.println("Error: " + e.getMessage());
                            }
                            return null;
                        }).join();
                    });
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }).join();

        long end = System.currentTimeMillis();
        System.out.println("4. Total execution time: " + (end - start) + " ms");
    }

    public static void printAllPostsWithUserAndCommentsAnd2Threads() {
        System.out.println("5. Imprimer tous les posts avec les commentaires et les détails de l'utilisateur en utilisant 2 threads uniquement");
        long start = System.currentTimeMillis();

        // TODO 5 (challenge optionnel) : Même exercice que précédemment, mais en utilisant que deux Threads pour ApiService.
        //  Nous vous conseillons de créer une nouvelle classe ApiServiceWithExecutor qui contiendra un ExecutorService.
        //  Affichez tous les posts, les commentaires et les détails de l'utilisateur sous un format du
        //  genre : "Post (postId:1) : {post details}
        //           Comments: (postId:1) : [{comments details}]
        //           User: (postId:1) : {user details}"
        //  Pour chaque "post", vous devez lancer en parallèle toute les requêtes pour
        //  récupérer les commentaires et les détails de l'utilisateur. De plus, vous devez faire attention à
        //  attendre que tant les commentaires que les détails de l'utilisateur soient récupérés avant d'afficher
        //  toutes les infos pour un post donné.


        long end = System.currentTimeMillis();
        System.out.println("5. Total execution time: " + (end - start) + " ms");
    }
}

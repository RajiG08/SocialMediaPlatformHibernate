package com.example.main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Scanner;

import com.example.config.AppConfig;
import com.example.model.Comment;
import com.example.model.Post;
import com.example.model.User;
import com.example.service.AdminService;
import com.example.service.BlockService;
import com.example.service.CommentService;
import com.example.service.FriendService;
import com.example.service.MessageService;
import com.example.service.PostService;
import com.example.service.UserService;

@Configuration
@ComponentScan(basePackages = "com.example")
public class Main {

    private final UserService userService;
    private final AdminService adminService;
    private final PostService postService;
    private final FriendService friendService;
    private final MessageService messageService;
    private final BlockService blockService;
    private final CommentService commentService;
    private static final System.Logger logger = System.getLogger(Main.class.getName());

    public Main(UserService userService, AdminService adminService, CommentService commentService, PostService postService,
                FriendService friendService, MessageService messageService, BlockService blockService) {
        this.userService = userService;
        this.adminService = adminService;
        this.postService = postService;
        this.friendService = friendService;
        this.messageService = messageService;
        this.blockService = blockService;
		this.commentService = commentService;
    }

    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {
            Main mainApp = context.getBean(Main.class);
            mainApp.start();
        }
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            logger.log(System.Logger.Level.INFO, "Choose an option: 1. User 2. Admin 3. Exit");
            int option = scanner.nextInt();
            scanner.nextLine();

            if (option == 1) {
                handleUserOptions(scanner);
            } else if (option == 2) {
                handleAdminOptions(scanner);
            } else {
                break;
            }
        }
        scanner.close();
    }

    private void handleUserOptions(Scanner scanner) {
        logger.log(System.Logger.Level.INFO, "1. Sign Up 2. Login");
        int option = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (option == 1) {
            signUpUser(scanner);
        } else if (option == 2) {
            loginUser(scanner);
        }
    }

    private void signUpUser(Scanner scanner) {
        User user = new User();
        logger.log(System.Logger.Level.INFO, "Enter username:");
        user.setUsername(scanner.nextLine().trim());
        logger.log(System.Logger.Level.INFO, "Enter name:");
        user.setName(scanner.nextLine().trim());
        logger.log(System.Logger.Level.INFO, "Enter password:");
        user.setPassword(scanner.nextLine().trim());
        logger.log(System.Logger.Level.INFO, "Enter email:");
        user.setEmail(scanner.nextLine().trim());
        logger.log(System.Logger.Level.INFO, "Enter privacy status:");
        user.setPrivacyStatus(scanner.nextLine().trim());

        if (user.getUsername().isEmpty() || user.getName().isEmpty() ||
                user.getPassword().isEmpty() || user.getEmail().isEmpty() ||
                user.getPrivacyStatus().isEmpty()) {
            logger.log(System.Logger.Level.INFO, "All fields are required.");
        } else {
            boolean success = userService.signUp(user);
            if (success) {
                logger.log(System.Logger.Level.INFO, "User signed up successfully.");
            } else {
                logger.log(System.Logger.Level.INFO, "User already exists.");
            }
        }
    }

    private void loginUser(Scanner scanner) {
        logger.log(System.Logger.Level.INFO, "Enter username:");
        String username = scanner.nextLine().trim();
        logger.log(System.Logger.Level.INFO, "Enter password:");
        String password = scanner.nextLine().trim();

        if (username.isEmpty() || password.isEmpty()) {
            logger.log(System.Logger.Level.INFO, "Username and password are required.");
        } else {
            boolean success = userService.login(username, password);
            if (success) {
                logger.log(System.Logger.Level.INFO, "User logged in successfully.");
                int userId = userService.getUserIdByUsername(username);
                displayUserMenu(scanner, userId);
            } else {
                logger.log(System.Logger.Level.INFO, "Invalid credentials.");
            }
        }
    }

    private void handleAdminOptions(Scanner scanner) {
        logger.log(System.Logger.Level.INFO, "Enter username:");
        String username = scanner.nextLine().trim();
        logger.log(System.Logger.Level.INFO, "Enter password:");
        String password = scanner.nextLine().trim();

        if (username.isEmpty() || password.isEmpty()) {
            logger.log(System.Logger.Level.INFO, "Username and password are required.");
        } else {
            boolean success = adminService.login(username, password);
            if (success) {
                logger.log(System.Logger.Level.INFO, "Admin logged in successfully.");
                displayAdminMenu();
            } else {
                logger.log(System.Logger.Level.INFO, "Invalid credentials.");
            }
        }
    }

    private void displayAdminMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            logger.log(System.Logger.Level.INFO, "Choose an option: 1. Remove User 2. Remove Post 3. View Users 4. View Posts 5. Logout");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (option == 1) {
                logger.log(System.Logger.Level.INFO, "Enter the user ID to Remove:");
                int userId = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                boolean success = adminService.removeUser(userId);
                if (success) {
                    logger.log(System.Logger.Level.INFO, "User removed successfully.");
                } else {
                    logger.log(System.Logger.Level.INFO, "Failed to remove User.");
                }
            } else if (option == 2) {
                logger.log(System.Logger.Level.INFO, "Enter the post ID:");
                int postId = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                boolean success = adminService.removePost(postId);
                if (success) {
                    logger.log(System.Logger.Level.INFO, "Post removed successfully.");
                } else {
                    logger.log(System.Logger.Level.INFO, "Failed to remove Post.");
                }
            } else if (option == 4) {
                List<Post> postFeed = postService.getAllPost(0);
                for (Post p : postFeed) {
                    logger.log(System.Logger.Level.INFO, "Post ID: " + p.getPostId());
                    logger.log(System.Logger.Level.INFO, "Post Text: " + p.getPostText());
                    logger.log(System.Logger.Level.INFO, "Likes: " + p.getLikesCount());
                    logger.log(System.Logger.Level.INFO, "Comments: " + p.getCommentCount());
                    logger.log(System.Logger.Level.INFO, "-------------------------");
                }
            } else if (option == 3) {
                List<User> users = userService.getAllUsers(0);
                for (User u : users) {
                    logger.log(System.Logger.Level.INFO, "User ID: " + u.getUserId() + ", Username: " + u.getUsername());
                }
            } else {
                break;
            }
        }
    }

    private void displayUserMenu(Scanner scanner, int userId) {
        while (true) {
            logger.log(System.Logger.Level.INFO, "Choose an option: 1. Feeds 2. Post 3. View Users 4. Message 5.Block/Unblock User 6. Logout");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (option == 1) {
                List<Post> postFeed = postService.getAllPost(0);
                int index = 1; // Start indexing posts from 1

                for (Post p : postFeed) {
                    logger.log(System.Logger.Level.INFO, "Post Index: " + index);
                    logger.log(System.Logger.Level.INFO, "Post ID: " + p.getPostId());
                    logger.log(System.Logger.Level.INFO, "Post Text: " + p.getPostText());
                    logger.log(System.Logger.Level.INFO, "Likes: " + p.getLikesCount());
                    logger.log(System.Logger.Level.INFO, "Comments: " + p.getCommentCount());
                    logger.log(System.Logger.Level.INFO, "-------------------------");
                    index++;
                }
                
                Scanner scanner1 = new Scanner(System.in);
                logger.log(System.Logger.Level.INFO, "Enter the index of the post you want to interact with: ");
                int chosenIndex = scanner1.nextInt();

                if (chosenIndex > 0 && chosenIndex <= postFeed.size()) {
                    Post chosenPost = postFeed.get(chosenIndex - 1); // Get the selected post
                    handlePostInteraction(chosenPost, userId); // Pass the selected post to the method
                } else {
                    logger.log(System.Logger.Level.WARNING, "Invalid post index selected.");
                }
                scanner1.close();
                
            }
           else if (option == 2) {
                handlePostOptions(scanner, userId);
            } else if (option == 3) {
                List<User> friends = userService.getAllUsers(userId);
                for (User friend : friends) {
                    logger.log(System.Logger.Level.INFO, "User ID: " + friend.getUserId() + ", Username: " + friend.getUsername());
                }
                handleFriendOptions(scanner, userId);
            } else if (option == 4) {
                handleMessageOptions(scanner, userId);
            } else if (option == 5) {
                handleBlockOptions(scanner, userId);
            }
              else {
                break;
            }
        }
     
        
    }

    private void handleBlockOptions(Scanner scanner, int userId) {
        while (true) {
            logger.log(System.Logger.Level.INFO, "Choose an option: 1. Block User 2. Unblock User 3. Back");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (option == 1) {
                logger.log(System.Logger.Level.INFO, "Enter the user ID to block:");
                int friendId = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                boolean success = blockService.blockUser(userId, friendId);
                if (success) {
                    logger.log(System.Logger.Level.INFO, "User blocked successfully.");
                } else {
                    logger.log(System.Logger.Level.INFO, "Failed to block user.");
                }
            } else if (option == 2) {
                logger.log(System.Logger.Level.INFO, "Enter the user ID to unblock:");
                int friendId = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                boolean success = blockService.unblockUser(userId, friendId);
                if (success) {
                    logger.log(System.Logger.Level.INFO, "User unblocked successfully.");
                } else {
                    logger.log(System.Logger.Level.INFO, "Failed to unblock user.");
                }
            } else {
                break;
            }
        }
    }

    
    private void handlePostInteraction(Post p, int userId) {
        logger.log(System.Logger.Level.INFO, "Choose an option for post ID " + p.getPostId() + ":");
        logger.log(System.Logger.Level.INFO, "1. Like");
        logger.log(System.Logger.Level.INFO, "2. Comment");
        logger.log(System.Logger.Level.INFO, "3. Next Post");

        Scanner scanner = new Scanner(System.in);
        int interactionOption = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (interactionOption) {
            case 1:
                handleLikePost(p);
                break;

            case 2:
                handleCommentPost(p, userId);
                break;

            case 3:
                // Move to the next post in the feed or handle accordingly
                break;

            default:
                logger.log(System.Logger.Level.INFO, "Invalid option.");
                break;
        }
    }
    private void handleLikePost(Post p) {
        logger.log(System.Logger.Level.INFO, "You liked the post.");
        p.setLikesCount(p.getLikesCount() + 1); 
        
        postService.updatePost(p); 
    }


    private void handleCommentPost(Post p, int userId) {
        Scanner scanner = new Scanner(System.in);
        logger.log(System.Logger.Level.INFO, "Enter your comment:");
        String commentText = scanner.nextLine().trim();
        User user = userService.getUserbyId(userId);
        if (!commentText.isEmpty()) {
            Comment comment = new Comment();
            comment.setPost(p);
            comment.setUser(user); // Assuming currentUser is the logged-in user
            comment.setCommentText(commentText);

            p.getComments().add(comment); // Add comment to post
            p.setCommentCount(p.getComments().size()); // Update comment count
            commentService.saveComment(comment); // Save the new comment
            postService.updatePost(p); // Update the post in database

            logger.log(System.Logger.Level.INFO, "Comment added successfully.");
        } else {
            logger.log(System.Logger.Level.INFO, "Comment cannot be empty.");
        }
    }

    
    private void handlePostOptions(Scanner scanner, int userId) {
        while (true) {
            logger.log(System.Logger.Level.INFO, "Choose an option: 1. Add Post 2. View My Posts 3. Back");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (option == 1) {
                logger.log(System.Logger.Level.INFO, "Enter your post text:");
                String postText = scanner.nextLine().trim();

                if (postText.isEmpty()) {
                    logger.log(System.Logger.Level.INFO, "Post text is required.");
                } else {
                	
                	 User user = userService.getUserbyId(userId); // Fetch the currently logged-in user

                     if (user == null) {
                         logger.log(System.Logger.Level.INFO, "No user is logged in.");
                         continue;
                     }
                    Post post = new Post();
                    post.setUser(user);
                    post.setPostText(postText);
                    boolean success = postService.addPost(post);
                    if (success) {
                        logger.log(System.Logger.Level.INFO, "Post added successfully.");
                    } else {
                        logger.log(System.Logger.Level.INFO, "Failed to add post.");
                    }
                }
            } else if (option == 2) {
                List<Post> userPosts = postService.getUserPosts(userId);
                if (userPosts.isEmpty()) {
                    logger.log(System.Logger.Level.INFO, "No posts yet.");
                } else {
                    for (Post p : userPosts) {
                        logger.log(System.Logger.Level.INFO, "Post ID: " + p.getPostId());
                        logger.log(System.Logger.Level.INFO, "Post Text: " + p.getPostText());
                        logger.log(System.Logger.Level.INFO, "Likes: " + p.getLikesCount());
                        logger.log(System.Logger.Level.INFO, "Comments: " + p.getCommentCount());
                        logger.log(System.Logger.Level.INFO, "-------------------------");
                    }
                }
            }

             else {
                break;
            }
        }
    }
    
    private void handleFriendOptions(Scanner scanner, int userId) {
        while (true) {
            logger.log(System.Logger.Level.INFO, "Choose an option: 1. Follow User 2. Unfollow User 3. View Followers 4. Back");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (option == 1) {
                logger.log(System.Logger.Level.INFO, "Enter user's ID to follow:");
                int friendId = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                boolean success = friendService.addFriend(userId, friendId);
                if (success) {
                    logger.log(System.Logger.Level.INFO, "Following added successfully.");
                } else {
                    logger.log(System.Logger.Level.INFO, "Failed to follow User.");
                }
            } else if (option == 2) {
                logger.log(System.Logger.Level.INFO, "Enter  user'S ID to remove as follwer:");
                int friendId = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                boolean success = friendService.removeFriend(userId, friendId);
                if (success) {
                    logger.log(System.Logger.Level.INFO, "Unfollowed Successfully.");
                } else {
                    logger.log(System.Logger.Level.INFO, "Failed UnFollow.");
                }
            } else if (option == 3) {
                List<User> friends = friendService.getFriends(userId);
                if (friends.isEmpty()) {
                    logger.log(System.Logger.Level.INFO, "No followers found.");
                } 
                else {
                    for (User friend : friends) {
                        logger.log(System.Logger.Level.INFO, "User ID: " + friend.getUserId() + ", Username: " + friend.getUsername());
                    }
                }
            } else {
                break;
            }
        }
    }


    private void handleMessageOptions(Scanner scanner, int userId) {
        while (true) {
            logger.log(System.Logger.Level.INFO, "Choose an option: 1. Send Message 2. View Messages 3. Back");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (option == 1) {
                logger.log(System.Logger.Level.INFO, "Enter recipient's user ID:");
                int recipientId = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                logger.log(System.Logger.Level.INFO, "Enter your message:");
                String messageText = scanner.nextLine().trim();

                if (messageText.isEmpty()) {
                    logger.log(System.Logger.Level.INFO, "Message text is required.");
                } else {
                    boolean success = messageService.sendMessage(userId, recipientId, messageText);
                    if (success) {
                        logger.log(System.Logger.Level.INFO, "Message sent successfully.");
                    } else {
                        logger.log(System.Logger.Level.INFO, "Failed to send message.");
                    }
                }
            } else if (option == 2) {
                List<String> messages = messageService.getMessages(userId);
                for (String message : messages) {
                    logger.log(System.Logger.Level.INFO, message);
                }
            } else {
                break;
            }
        }
    }
}

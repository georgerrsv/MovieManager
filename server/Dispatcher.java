public class Dispatcher {
    private Database db;

    public Dispatcher(Database db) {
        this.db = db;
    }

    public Message invoke(String jsonStr) {
        Message message = Message.fromJson(jsonStr);
        String methodId = String.valueOf(message.getMethodId());
        String arguments = message.getArguments();
        MovieSkeleton movieSkeleton = new MovieSkeleton(db);
        UserSkeleton userSkeleton = new UserSkeleton(db);
        String responseArguments;

        switch (methodId) {
            case "1":
                responseArguments = movieSkeleton.addMovie(arguments);
                break;
            case "2":
                responseArguments = movieSkeleton.removeMovie(arguments);
                break;
            case "3":
                responseArguments = movieSkeleton.showDetails(arguments);
                break;
            case "4":
                responseArguments = movieSkeleton.showCatalog();
                break;
            case "add":
                responseArguments = userSkeleton.addUser(arguments);
                break;
            case "login":
                responseArguments = userSkeleton.login(arguments);
                break;
            case "remove":
                responseArguments = userSkeleton.removeUser(arguments);
                break;
            case "show":
                responseArguments = userSkeleton.showUserDetails(arguments);
                break;
            default:
                responseArguments = "Unknown method";
                break;
        }

        return new Message(1, message.getObjectReference(), methodId, responseArguments);
    }
}
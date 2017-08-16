import javax.annotation.Nonnull;

public class Caller {
    public void callOfNullable() {
        String env = System.getenv("hallo");
        printer(env);
    }

    public void printer(@Nonnull String nullable) {
        System.out.println(nullable.toString());
    }
}

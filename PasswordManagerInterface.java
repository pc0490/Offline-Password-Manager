public interface PasswordManagerInterface {
    String get_Acc(String account);
    int add_Acc(String account, String passwd);
    int remove_Acc(String account);
}

package emailAddressPackage;


public class testdrive {
    public static void main(String args[]) {

        EmailAddressAbstact grupMuh = new GroupEmailAddress("mailGrup-muhendis", "muhendis@gtu.edu.tr");
        EmailAddressAbstact grupMimar = new GroupEmailAddress("mailGrup-mimar", "mimar@gtu.edu.tr");
        EmailAddressAbstact grupTemel = new GroupEmailAddress("mailGrup-temelBilimler", "temelBilimler@gtu.edu.tr");
        EmailAddressAbstact gruptopluluk = new GroupEmailAddress("mailGrup-topluluk", "topluluk@gtu.edu.tr");
        EmailAddressAbstact allGroups = new GroupEmailAddress("ALL GROUPS", "allgroups@gtu.edu.tr");

        allGroups.add(grupMuh);
        allGroups.add(grupMimar);
        allGroups.add(grupTemel);

        grupMuh.add(new PersonalEmailAddress("MuhPersonal1","MuhPersonal1@gtu.edu.tr"));
        grupMuh.add(new PersonalEmailAddress("MuhPersonal2","MuhPersonal2@gtu.edu.tr"));

        grupMimar.add(new PersonalEmailAddress("MimarPersonal1","MimarPersonal1@gtu.edu.tr"));
        grupMimar.add(new PersonalEmailAddress("MimarPersonal2","MimarPersonal2@gtu.edu.tr"));

        grupTemel.add(new PersonalEmailAddress("TemelBilimlerPersonal1","TemelBilimlerPersonal1@gtu.edu.tr"));
        grupTemel.add(new PersonalEmailAddress("TemelBilimlerPersonal2","TemelBilimlerPersonal2@gtu.edu.tr"));

        gruptopluluk.add(new PersonalEmailAddress(
                "topluluk","topluluk@gtu.edu.tr"));

        grupMimar.add(gruptopluluk);

        EmailAddressAll allEmailAddress = new EmailAddressAll(allGroups);
        allEmailAddress.printGroups();
    }
}
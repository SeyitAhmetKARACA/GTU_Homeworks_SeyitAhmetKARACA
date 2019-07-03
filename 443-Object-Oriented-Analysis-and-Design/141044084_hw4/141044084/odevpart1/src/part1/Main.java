package part1;

public class Main {
    public static void main(String[] args) {

        Student student = new Student();

        // ready to fit to graduate
        System.out.println("---ready to fit to graduate----------------------------------------------------------------");
        student = new Student();
        student.exercise();
        student.perseveranceAndHardWork();

        // ready to graduate
        System.out.println("---ready to graduate-----------------------------------------------------------------------");
        student = new Student();
        student.perseveranceAndHardWork();

        // ready to needing sleep -> chronic illness
        System.out.println("---ready to needing sleep -> chronic illness-----------------------------------------------");
        student = new Student();
        student.outTillLate();
        student.coffeeAndWork();

         //ready to unable to become a rod for an axe
        System.out.println("---ready to unable to become a rod for an axe----------------------------------------------");
        student.buyingGTX1080();

        student = new Student();
        student.cheating();

        // ready to needing sleep to ready
        System.out.println("---ready to needing sleep to ready---------------------------------------------------------");
        student = new Student();
        student.outTillLate();
        student.sleep();

        // Errors
        System.out.println("---Errors----------------------------------------------------------------------------------");
        student = new Student();
        student.coffeeAndWork();
        student.outTillLate(); // valid
        student.cheating();
        student.perseveranceAndHardWork();
        student.buyingGTX1080();
        student.exercise();
    }
}
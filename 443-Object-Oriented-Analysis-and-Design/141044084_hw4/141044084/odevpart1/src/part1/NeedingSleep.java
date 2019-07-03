package part1;


import part1.Student;

public class NeedingSleep extends AbstractState {
    public NeedingSleep(Student student) {
        super(student);
    }

    @Override
    public void coffeeAndWork() {
        changeState("Coffee & work", getStudent().getChronicIllnessState());
    }

    @Override
    public void sleep() {
        changeState("SLEEP", getStudent().getReadyState());
    }
}
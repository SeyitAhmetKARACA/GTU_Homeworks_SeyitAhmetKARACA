package part1;

import part1.Student;

public class Fit extends AbstractState {
    public Fit(Student student) {
        super(student);
    }

    @Override
    public void perseveranceAndHardWork() {
        changeState("Perseverance and hard work", getStudent().getGraduateState());
    }
}
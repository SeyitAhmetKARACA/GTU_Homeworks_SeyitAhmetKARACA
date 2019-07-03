package part1;


import part1.Student;

public class Ready extends AbstractState {

    public Ready(Student student) {
        super(student);
    }

    @Override
    public void buyingGTX1080() {
        changeState("Buying GTX1080", getStudent().getUnableToBecomeARodForAnAxeState());
    }

    @Override
    public void outTillLate() {
        changeState("Out till late", getStudent().getNeedingSleepState());
    }

    @Override
    public void perseveranceAndHardWork() {
        changeState("Perseverance and & work", getStudent().getGraduateState());
    }

    @Override
    public void exercise() {
        changeState("Exercise", getStudent().getFitState());
    }

    @Override
    public void cheating() {
        changeState("Cheating", getStudent().getUnableToBecomeARodForAnAxeState());
    }
}
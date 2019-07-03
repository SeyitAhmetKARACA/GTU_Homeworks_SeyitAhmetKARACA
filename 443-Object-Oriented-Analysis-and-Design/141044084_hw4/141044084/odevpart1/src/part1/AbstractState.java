package part1;

public abstract class AbstractState implements State {
    private Student student;

    public AbstractState(Student student) {
        this.student = student;
    }

    protected void changeState(String action, State newState) {
        System.out.println("Current state : " + getStudent().getStateName()+" + "+ action);
        student.setState(newState);
        System.out.println("New state : " + newState.toString() + "\n");
    }

    protected Student getStudent() {
        return student;
    }

    @Override
    public void coffeeAndWork() {
        NotSupportedActionMessage("coffee & work");
    }

    @Override
    public void sleep() {
        NotSupportedActionMessage("sleep");
    }

    @Override
    public void outTillLate() {
        NotSupportedActionMessage("out till late");
    }

    @Override
    public void perseveranceAndHardWork() {
        NotSupportedActionMessage("perseverance and hard work");
    }

    @Override
    public void exercise() {
        NotSupportedActionMessage("exercise");
    }

    @Override
    public void buyingGTX1080() {
        NotSupportedActionMessage("buying GTX1080");
    }

    @Override
    public void cheating() {
        NotSupportedActionMessage("cheating");
    }

    private void NotSupportedActionMessage(String action) {
        System.out.println("State "+this.getClass().getSimpleName() + " is not supporting action " + action);
    }

    @Override
    public String toString(){
        return this.getClass().getSimpleName();
    }
}

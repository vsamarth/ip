public class Duke {
    private UserInterface ui;
    private TaskList list;

    public Duke() {
        ui = new UserInterface();
        list = new TaskList();

    }

    public void run() {
        ui.showGreeting();
        while (true) {
            // Check if there are available lines to read
            if (!ui.hasNextLine()) {
                return;
            }


            String input = ui.getInput();

            if (input.equals("bye")) {
                ui.showExitMessage();
                return;
            } else if (input.startsWith("mark")) {
                int id = Integer.parseInt(input.substring(5));
                Task t = list.stream().filter(task -> task.id() == id).findFirst().get();
                t.markCompleted();
                ui.showMessage("Nice! I've marked this task as done: \n " + t.toString());
            } else if (input.startsWith("unmark")) {
                int id = Integer.parseInt(input.substring(7));
                Task t = list.stream().filter(task -> task.id() == id).findFirst().get();
                t.markPending();
                ui.showMessage("Nice! I've marked this task as pending: \n " + t.toString());
            }
             else if (input.startsWith("todo")) {
                String desc = input.substring(5);
                Task t = new Todo(list.nextId(), desc);
                list.add(t);
                ui.showMessage("added: " + t.description());
            } else if (input.startsWith("deadline")) {
                String desc = input.substring(9);
                String dueDate = desc.substring(desc.indexOf("/by") + 4);
                desc = desc.substring(0, desc.indexOf("/by") - 1);
                Task t = new Deadline(list.nextId(), desc, dueDate);
                list.add(t);
                ui.showMessage("added: " + t.description());
            } else if (input.startsWith("event")) {
                String desc = input.substring(6);
                String from = desc.substring(desc.indexOf("/from") + 6, desc.indexOf("/to") - 1);
                String to = desc.substring(desc.indexOf("/to") + 4);
                desc = desc.substring(0, desc.indexOf("/from") - 1);
                Task t = new Event(list.nextId(), desc, from, to);
                list.add(t);
                ui.showMessage("added: " + t.description());

            } else if (input.equals("list")) {
                ui.showTasks(list);
            } else {
                ui.showMessage("I'm sorry, but I don't know what that means :-(");
            }
        }

    }

    public static void main(String[] args) {
        new Duke().run();
    }
}

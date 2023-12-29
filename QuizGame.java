import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

class QuizQuestion {
  String question;
  List<String> options;
  int correctOption;

  public QuizQuestion(String question, List<String> options, int correctOption) {
    this.question = question;
    this.options = options;
    this.correctOption = correctOption;
  }
}

public class QuizGame {

  private List<QuizQuestion> quizQuestions;
  private int currentQuestionIndex;
  private int userScore;
  private Timer timer;

  public QuizGame() {
    initializeQuestions();
    currentQuestionIndex = 0;
    userScore = 0;
  }

  private void initializeQuestions() {
    quizQuestions = new ArrayList<>();

    // Add new quiz questions, options, and correct answers
    quizQuestions.add(new QuizQuestion("Who wrote 'Harry Potter'?\n select 1-4:",
        List.of("J.K. Rowling", "Agatha Christie", "Harper Lee", "F. Scott Fitzgerald"), 0));

    quizQuestions.add(new QuizQuestion("What is the largest living animal on Earth?\n select 1-4:",
        List.of("Blue Whale", "Elephant", "Giraffe", "Hippopotamus"), 0));

    quizQuestions.add(new QuizQuestion("What is the symbol of peace?\n select 1-4:",
        List.of("Dove", "Octopus", "Eagle", "Butterfly"), 0));

    quizQuestions.add(new QuizQuestion("What is the capital of Australia?\n select 1-4:",
        List.of("Sydney", "Melbourne", "Canberra", "Perth"), 2));

    quizQuestions.add(new QuizQuestion("What is the name of the tallest mountain in the world?\n select 1-4:",
        List.of("Mount Everest", "Mount Kilimanjaro", "Mount Fuji", "Mount Elbrus"), 0));

    
  }

  private void displayQuestion() {
    QuizQuestion currentQuestion = quizQuestions.get(currentQuestionIndex);

    System.out.println("Question: " + currentQuestion.question);
    for (int i = 0; i < currentQuestion.options.size(); i++) {
      System.out.println((i + 1) + ". " + currentQuestion.options.get(i));
    }
  }

  private void displayPrompt() {
    System.out.print("Select your answer (1-" + quizQuestions.get(currentQuestionIndex).options.size() + "): ");
  }

  private void startTimer(int seconds) {
    timer = new Timer();
    timer.scheduleAtFixedRate(new TimerTask() {
      int count = seconds;

      @Override
      public void run() {
        System.out.print("\rTime remaining: " + count + " seconds. ");
        count--;

        if (count < 0) {
          System.out.println("\nTime's up! Moving to the next question.");
          nextQuestion();
        }
      }
    }, 0, 1000);
  }

  private void stopTimer() {
    if (timer != null) {
      timer.cancel();
    }
  }

  private void nextQuestion() {
    stopTimer();

    if (currentQuestionIndex < quizQuestions.size() - 1) {
      currentQuestionIndex++;
      displayQuestion();
      displayPrompt();
      startTimer(10); 
    } else {
      endQuiz();
    }
  }

  private void endQuiz() {
    System.out.println("\nQuiz completed! Your final score: " + userScore + "/" + quizQuestions.size());
  }

  public void playQuiz() {
    displayQuestion();
    displayPrompt();
    startTimer(10); 

    Scanner scanner = new Scanner(System.in);
    while (currentQuestionIndex < quizQuestions.size()) {
      int userAnswer;

      try {
        userAnswer = scanner.nextInt();
      } catch (java.util.InputMismatchException e) {
        System.out.println("Invalid input. Please enter a number.");
        scanner.nextLine(); 
        continue;
      }

      if (userAnswer < 1 || userAnswer > quizQuestions.get(currentQuestionIndex).options.size()) {
        System.out.println("Invalid input. Please enter a number between 1 and "
            + quizQuestions.get(currentQuestionIndex).options.size() + ".");
        continue;
      }

      if (userAnswer == quizQuestions.get(currentQuestionIndex).correctOption + 1) {
        System.out.println("Correct!\n");
        userScore++;
      } else {
        System.out.println("Incorrect. The correct answer was: "
            + (quizQuestions.get(currentQuestionIndex).correctOption + 1) + ". "
            + quizQuestions.get(currentQuestionIndex).options
                .get(quizQuestions.get(currentQuestionIndex).correctOption) + "\n");
      }

      nextQuestion();
    }

    endQuiz();
    scanner.close();
  }

  public static void main(String[] args) {
    QuizGame quizGame = new QuizGame();
    quizGame.playQuiz();
  }
}

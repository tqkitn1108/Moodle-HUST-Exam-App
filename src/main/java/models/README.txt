DBInteract: class dùng để kết nối với database
gồm:  + void insertQuestion(Question q): thêm Question q vào db
      + void createNewQuiz(Quiz quiz): thêm 1 Quiz mới vào db
      + List<Question> getQuestionsBelongToQuiz(Quiz quiz): lấy tất cả các Question của Quiz
      + List<Quiz> getAllQuizzes(): lấy tất cả các Quiz từ db
      
DataInteract: class kết nối với các data bên ngoài
gồm   + List<Question> getQuestionsFromDocFile(String path): lấy Question từ file doc với đường dẫn path

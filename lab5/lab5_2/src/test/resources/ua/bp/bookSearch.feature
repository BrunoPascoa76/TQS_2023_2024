Feature: Search Books
  Background:
    Given a library with the following books:
      | Title | Author  | Published |
      | The Hobbit  | J.R.R.Tolken  | 1937  |
      | Clean Code  | Robert C. Martin | 2008 |
      | The Pragmatic Programmer | Andrew Hunt, David Thomas  | 1999 |
      | Outliers | Malcolm Gladwell | 2008 |
      
  Scenario: Search by title
    When I search for a book with the title "The Hobbit"
    Then I should find the following books:
      | Title | Author  | Published |
      | The Hobbit  | J.R.R.Tolken  | 1937  |
    
  Scenario: Search by title (no books)
    When I search for a book with the title "Os Maias"
    Then I should find no books

  Scenario: Search by author
    When I search for a book written by "Robert C. Martin"
    Then I should find the following books:
      | Title | Author  | Published |
      | Clean Code  | Robert C. Martin | 2008 |

  Scenario: Search by author (no books)
    When I search for a book written by "J.K. Rowling"
    Then I should find no books

  Scenario: Search by year
    When I search for a book released between 1998 and 2010
    Then I should find the following books:
      | Title | Author  | Published |
      | Clean Code  | Robert C. Martin | 2008 |
      | The Pragmatic Programmer | Andrew Hunt, David Thomas  | 1999 |
      | Outliers | Malcolm Gladwell | 2008 |
  
  Scenario: Search by year (no books)
    When I search for a book released between 1888 and 1900
    Then I should find no books


from userProxy import userProxy
from movieProxy import movieProxy
from movie import Movie
from user import User
import time
from os import system

userProxy = userProxy()
movieProxy = movieProxy()

def tryNum(value):
    try:
        int(value)
        return True
    except ValueError:
        return False

def tryInput(prompt):
    while True:
        value = input(prompt)
        if tryNum(value):
            return int(value)
        else:
            print("Error: invalid value")


print("-------------------------------------")
print("Movie catalog manager")
print("-------------------------------------")
print("Options:\n1-Create account\n2-Login\n3-Clear screen\n4-Exit")
print("-----------------------------------")

while True:

    option = tryInput("Choose your option: ")

    if option == 1:
        fname = input("Enter your first name: ")
        lname = input("Enter your last name: ")
        email = input("Email: ")
        password = input("Password: ")
        user = User(fname, lname, email, password)
        userProxy.addUser(user)

    elif option == 2:
        email = input("Email: ")
        password = input("Password: ")
        userProxy.login(email, password)
        print("Welcome!\n")

        while True:

            print("Options:\n1-Add movie\n2-Remove movie\n3-Show movie details\n4-Exit")
            option = tryInput("Choose your option: ")

            if option == 1:
                title = input("Title: ")
                director = input("Director: ")
                year = tryInput("Year: ")
                duration = tryInput("Duration (in minutes): ")
                genre = input("Genre: ")
                classification = input("Classification: ")
                rating = tryInput("Rating: ")
                description = input("Description: ")
                movie = Movie(title, director, year, duration, genre, classification, rating, description)
                movieProxy.addMovie(movie)

            elif option == 2:
                id = tryInput("Movie id: ")
                movieProxy.removeMovie(id)

            elif option == 3:
                id = tryInput("Movie id: ")
                movieProxy.showDetails(id)

            elif option == 4:
                break

            else:
                print("Error: invalid option")

    elif option == 3:
        system('clear')
        print("-------------------------------------")
        print("Movie catalog manager")
        print("-------------------------------------")
        print("Options:\n1-Create account\n2-Login\n3-Clear screen\n4-Exit")
        print("-----------------------------------")

    elif option == 4:
        print("Logging out...")
        time.sleep(1)
        userProxy.close()
        movieProxy.close()
        break

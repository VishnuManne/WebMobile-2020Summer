//Rock Paper Scissors JavaScript File
//This program is for the computer to choose random number

function computer_option() {
    const rdmNum = Math.floor(Math.random()*3);
    if (rdmNum < 1) return "r";
    if (rdmNum < 2 && rdmNum >= 1) return "p";
    else return "s";
}

//Converting r, p, s to rock, paper and scissors
function convertToWord(letter) {
    if (letter === "r") return "ROCK";
    if (letter === "s") return "SCISSORS";
    else return "PAPER";
}

//three function here used to decide win, lose, and draw.
function win(userOption, computerOption) {
    document.querySelector(".results>p").innerHTML = " You choice is: " + convertToWord(userOption) + ". Computer choice is: " + convertToWord(computerOption) + ". YOU WIN! Wan't to Play again.";

}

function lose(userOption, computerOption) {
    document.querySelector(".results>p").innerHTML = " You choice is: " + convertToWord(userOption) + ". Computer choice is: " + convertToWord(computerOption) + ". YOU LOST! Wan't to Play Again.";
}

function draw(userOption, computerOption) {
    document.querySelector(".results>p").innerHTML = " You choice is: " + convertToWord(userOption) + ". Computer choice is: " + convertToWord(computerOption) + ". IT'S A DRAW! Wan't to Play Again.";
}


//Main function here
function rule(userOption) {
    const computerOption = computer_option();
    if (userOption + computerOption === "rs") {
        win(userOption, computerOption);
    } else if (userOption + computerOption === "pr") {
        win(userOption, computerOption);
    } else if (userOption + computerOption === "sp") {
        win(userOption, computerOption);
    } else if (userOption + computerOption === "rp") {
        lose(userOption, computerOption);
    } else if (userOption + computerOption === "ps") {
        lose(userOption, computerOption);
    } else if (userOption + computerOption === "sr") {
        lose(userOption, computerOption);
    } else {
        draw(userOption, computerOption);
    }
}
//Acquiring the user input
function main() {
    document.getElementById("rock").addEventListener("click", function () {
        rule("r");
    });
    document.getElementById("paper").addEventListener("click", function () {
        rule("p");
    });
    document.getElementById("scissors").addEventListener("click", function () {
        rule("s");
    })
}

main();
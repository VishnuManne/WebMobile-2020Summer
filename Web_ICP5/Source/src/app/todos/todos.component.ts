import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-todos',
  templateUrl: './todos.component.html',
  styleUrls: ['./todos.component.css']
})
export class TodosComponent implements OnInit {
  public todos = [];
  public countDownTimes = [];
  public intervals = []
  public todo = '';
  public time = new Date();
  constructor() { }

  ngOnInit(): void {
  }

  saveTodo(){
    const todoObject = {
      id: this.todos.length + 1,
      name: this.todo,
      expectedDate: new Date(this.time),
      completed: false,
      // countdowntime: this.getCountDown(new Date(this.time)),
    }
    this.getCountDown(todoObject.expectedDate, this.todos.length)
    this.todos.push(todoObject);
    console.log(this.todos)
  }

  countDown(todo){

  }

  getCountDown(todoTime, index) {
    this.intervals[index] = setInterval(() => {
      const now = new Date().getTime();
      const distance = todoTime.getTime() - now;
      const days = Math.floor(distance / (1000 * 60 * 60 * 24));
      const hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
      const minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
      const seconds = Math.floor((distance % (1000 * 60)) / 1000);
      this.countDownTimes[index]= {
        days,
        hours,
        minutes,
        seconds
      };
    }, 1000);
  }

  removeTodo(index) {
    this.todos.splice(index,1)
    this.countDownTimes.splice(index,1)
    clearInterval(this.intervals[index]);
  }

  toggleTodoComplete(index) {
    const todo = this.todos[index];
    todo['completed'] = !todo['completed'];
    this.todos[index] = todo;
    clearInterval(this.intervals[index]);
  }
}

import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  template: `
    <div class="container">
      <h1>Employee Management System</h1>
      <router-outlet></router-outlet>
    </div>
  `
})
export class AppComponent {
  title = 'employee-management';
}

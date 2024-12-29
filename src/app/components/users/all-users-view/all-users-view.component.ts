import { UserReadOnlyDTO } from '@/src/app/shared/interfaces/users';
import { ErrorService } from '@/src/app/shared/services/error.service';
import { UsersService } from '@/src/app/shared/services/users.service';
import { Component, inject } from '@angular/core';

@Component({
  selector: 'app-all-users-view',
  standalone: true,
  imports: [],
  templateUrl: './all-users-view.component.html',
  styleUrl: './all-users-view.component.css',
})
export class AllUsersViewComponent {
  userService = inject(UsersService);
  errorService = inject(ErrorService);
  userList: UserReadOnlyDTO[] = [];

  ngOnInit() {
    this.getUsers();
  }

  getUsers() {
    this.userService.getAllUsers().subscribe({
      next: (data: UserReadOnlyDTO[]) => {
        this.userList = data.sort((a: UserReadOnlyDTO, b: UserReadOnlyDTO) => {
          if (a.username < b.username) {
            return -1;
          }
          if (a.username > b.username) {
            return 1;
          }
          return 0;
        });
      },
      error: (error) => {
        this.errorService.errorMessage.set('Error while getting users');
        this.errorService.errorColor.set('red');
      },
    });
  }

  onDeleteClick(uuid: string, username: string) {
    this.userService.deleteUser(uuid, username);
  }
}

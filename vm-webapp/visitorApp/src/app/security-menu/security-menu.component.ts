import { Component, OnInit } from '@angular/core';
import { GuestAccessComponent} from './guest-access/guest-access.component';
import { GuestPassComponent } from './guest-pass/guest-pass.component';
import { GuestPendingComponent} from './guest-pending/guest-pending.component';


@Component({
  selector: 'app-security-menu',
  templateUrl: './security-menu.component.html',
  styleUrls: ['./security-menu.component.css']
})

export class SecurityMenuComponent implements OnInit {

  constructor() { }
  
  ngOnInit() {
  }

  links = ['Guest Access Request', 'Guest Details', 'Pending Details'];
  activeLink = this.links[0];
  background = '';

  toggleBackground() {
    this.background = this.background ? '' : 'primary';
  }

}

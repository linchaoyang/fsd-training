import { AuthService } from './../auth/auth.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from '../auth/user';
import { GlobalDefine } from 'config/global-define';

@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.scss']
})
export class IndexComponent implements OnInit {

  title = GlobalDefine.system;

  showSearch: boolean;

  user: User;

  constructor(private route: ActivatedRoute, private router: Router, private authService: AuthService) { }

  ngOnInit(): void {
    this.route.data.subscribe(
      (data: {showSearch: boolean}) => {
        this.showSearch = data.showSearch || true;
      }
    );

    this.user = this.authService.getUser();
  }
}

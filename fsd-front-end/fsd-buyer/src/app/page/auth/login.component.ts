import { GlobalDefine } from './../../../../config/global-define';
import { Component, OnInit } from '@angular/core';
import { AuthService } from './auth.service';
import { Router } from '@angular/router';
import { FormGroup, FormControl, Validators, FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  title = GlobalDefine.system;

  loginForm: FormGroup;

  toastShow = false;

  toastBody = '';

  constructor(public authService: AuthService, public router: Router, private formBuilder: FormBuilder) {
    const userNameFc = new FormControl('',
      Validators.compose([Validators.required, Validators.minLength(6), Validators.maxLength(15)]));
    const passwordFc = new FormControl('',
      Validators.compose([Validators.required, Validators.minLength(6), Validators.maxLength(15)]));

    this.loginForm = this.formBuilder.group({
      username: userNameFc,
      password: passwordFc
    });
  }

  ngOnInit(): void {
  }

  doLogin() {
    this.authService.login(this.loginForm.get('username').value, this.loginForm.get('password').value).subscribe(() => {
      if (this.authService.isLoggedIn()) {
        // Get the redirect URL from our auth service
        // If no redirect has been set, use the default
        const redirect = this.authService.redirectUrl ? this.router.parseUrl(this.authService.redirectUrl) : '/index';

        // Redirect the user
        this.router.navigateByUrl(redirect);
      }
    },
    err => { this.toastShow = true; this.toastBody = err.body.error; });
  }
}

import { LoginComponent } from './page/auth/login.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';


const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: '/index' },
  { path: 'login', component: LoginComponent },
  { path: 'index', loadChildren: () => import('./page/index/index.module').then(m => m.IndexModule) },
  { path: '**', redirectTo: '/login'},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

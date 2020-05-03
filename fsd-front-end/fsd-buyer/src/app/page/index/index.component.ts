import { AuthService } from './../auth/auth.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from '../auth/user';
import { GlobalDefine } from 'config/global-define';
import { Subject, Observable, from } from 'rxjs';
import { debounceTime, distinctUntilChanged, switchMap } from 'rxjs/operators';

@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.scss']
})
export class IndexComponent implements OnInit {

  title = GlobalDefine.system;

  showSearch: boolean;

  user: User;

  cardNumber = 0;

  private searchTerms = new Subject<string>();

  private searchSubscribe$: Observable<string>;

  private searchKeyword: string;

  constructor(private route: ActivatedRoute, private router: Router, private authService: AuthService) { }

  ngOnInit(): void {

    this.searchSubscribe$ = this.searchTerms.pipe(
      // wait 300ms after each keystroke before considering the term
      debounceTime(300),

      // ignore new term if same as previous term
      distinctUntilChanged(),

      // switch to new search observable each time the term changes
      switchMap(term => {
        this.searchKeyword = term.trim();
        console.log(this.searchKeyword);
        return term;
      })
    );

    this.route.data.subscribe(
      (data: { showSearch: boolean }) => {
        this.showSearch = data.showSearch || true;
      }
    );

    this.user = this.authService.getUser();

    // TODO read card number from api
    this.cardNumber = 1;

    this.router.navigate(['/index/layout'], { state: { searchKeyword: '' }});
  }

  // Push a search term into the observable stream.
  search(term: string): void {
    // this.searchTerms.next(term);
    // this.searchTerms.complete();
    // this.searchSubscribe$.subscribe();
    this.router.navigate(['/index/layout'], { state: { searchKeyword: term.trim() }});
    // console.log('search product: ' + this.searchKeyword);
  }
}

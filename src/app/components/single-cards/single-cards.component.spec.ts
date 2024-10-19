import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SingleCardsComponent } from './single-cards.component';

describe('SingleCardsComponent', () => {
  let component: SingleCardsComponent;
  let fixture: ComponentFixture<SingleCardsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SingleCardsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SingleCardsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

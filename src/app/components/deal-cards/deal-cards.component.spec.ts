import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DealCardsComponent } from './deal-cards.component';

describe('DealCardsComponent', () => {
  let component: DealCardsComponent;
  let fixture: ComponentFixture<DealCardsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DealCardsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DealCardsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

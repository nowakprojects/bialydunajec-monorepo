import {NgModule} from '@angular/core';
import {FaqComponent} from './component/faq/faq.component';
import {SharedModule} from '../shared/shared.module';
import {SuiAccordionModule, SuiSearchModule} from 'ng2-semantic-ui';
import {FaqCategoryComponent} from './component/faq/faq-category/faq-category.component';
import {FaqService} from './service/faq.service';
import {AboutCampRoutingModule} from './about-camp-routing.module';
import { PacklistComponent } from './component/packlist/packlist.component';

@NgModule({
  imports: [
    SharedModule,
    SuiAccordionModule,
    SuiSearchModule,
    AboutCampRoutingModule
  ],
  declarations: [FaqComponent, FaqCategoryComponent, PacklistComponent],
  providers: [FaqService]
})
export class AboutCampModule {
}

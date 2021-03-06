import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {campRegistrationsRoutingPaths} from './camp-registrations-routing.paths';
import {CampRegistrationsSettingsComponent} from './component/camp-registrations-settings/camp-registrations-settings.component';
import {CottageListComponent} from './component/cottage-list/cottage-list.component';
import {CottageEditComponent} from './component/cottage-edit/cottage-edit.component';
import {CampParticipantListComponent} from './component/camp-participant-list/camp-participant-list.component';
import {ShirtSettingsComponent} from './component/shirt-settings/shirt-settings.component';
import {ShirtOrdersComponent} from './component/shirt-orders/shirt-orders.component';
import {CampRegistrationsStatisticsComponent} from './component/camp-registrations-statistics/camp-registrations-statistics.component';
import {CampParticipantEditComponent} from './component/camp-participant-edit/camp-participant-edit.component';

const campRegistrationsRoutes: Routes = [
  {path: campRegistrationsRoutingPaths.root, component: CampRegistrationsSettingsComponent},
  {path: campRegistrationsRoutingPaths.cottages, component: CottageListComponent},
  {path: campRegistrationsRoutingPaths.newCampParticipant, component: CampParticipantEditComponent},
  {path: campRegistrationsRoutingPaths.campParticipants, component: CampParticipantListComponent},
  {path: campRegistrationsRoutingPaths.shirtSettings, component: ShirtSettingsComponent},
  {path: campRegistrationsRoutingPaths.shirtOrders, component: ShirtOrdersComponent},
  {path: campRegistrationsRoutingPaths.statistics, component: CampRegistrationsStatisticsComponent},
  {
    path: `${campRegistrationsRoutingPaths.cottages}/:cottageId`,
    component: CottageEditComponent
  },
  {
    path: `${campRegistrationsRoutingPaths.cottages}/:cottageId/${campRegistrationsRoutingPaths.editCottage}`,
    component: CottageEditComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(campRegistrationsRoutes)],
  exports: [RouterModule]
})
export class CampRegistrationsRoutingModule {
}

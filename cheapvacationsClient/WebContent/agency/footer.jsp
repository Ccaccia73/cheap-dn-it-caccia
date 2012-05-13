<%@ page import="com.cheapvacations.logic.*, com.cheapvacations.entities.*, com.cheapvacations.utility.*" %>

<%
	TravelAgencyManagerRemote ta = (TravelAgencyManagerRemote)request.getSession().getAttribute("agencyREF");
	FlightOffer fo_dep = ta.getPackageDepart();
	FlightOffer fo_ret = ta.getPackageReturn();
	HotelOffer ho_pack = ta.getPackageHotel();
%>


  


<table width="100%", height="100px" border="1px">
  <tr>
    <th width="200px">Departure</th>
    <th width="200px">Return</th>
    <th width="250px">Stay</th>
    <th width="100px"></th>
  </tr>
  <tr>
  	<td>
  		<table>
  			<tr>
  				<td>From:</td><td><strong><% if(fo_dep != null){ out.print(fo_dep.getReferenceFlight().getDeparture()); }; %></strong></td>
  			</tr>
  			<tr>
  				<td>To:</td><td><strong><% if(fo_dep != null){ out.print(fo_dep.getReferenceFlight().getArrival()); }; %></strong></td>
  			</tr>
  		</table>
  	</td>
  	<td>
  		<table>
  			<tr>
  				<td>From:</td><td><strong><% if(fo_ret != null){ out.print(fo_ret.getReferenceFlight().getDeparture()); }; %></strong></td>
  			</tr>
  			<tr>
  				<td>To:</td><td><strong><% if(fo_ret != null){ out.print(fo_ret.getReferenceFlight().getArrival()); }; %></strong></td>
  			</tr>
  		</table>
  	</td>
  	<td>
  		<table>
  			<tr>
  				<td>Hotel:</td><td><strong><% if(ho_pack != null){ out.print(ho_pack.getReferenceHotel().getName()); }; %></strong></td>
  			</tr>
  			<tr>
  				<td>City:</td><td><strong><% if(ho_pack != null){ out.print(ho_pack.getReferenceHotel().getCity()); }; %></strong></td>
  			</tr>
  		</table>
  	</td>
  	<td>
		<form action="/cheapvacationsClient/PackageServlet" method="get">
			<button name="op" value="8" style="margin-left:10px;">Clear Selection</button>
		</form>
  	</td>
  </tr>
</table> 

<!-- 
<div style="margin:auto;width:60%;">

<div style="display:inline; border-right:solid orange 1px; padding-left:10px;padding-right:10px;">
	Departure:<br/>
	< %
		if(fo_dep != null){
			out.print("from: "+fo_dep.getReferenceFlight().getDeparture()+"<br />");
			out.print("to: "+fo_dep.getReferenceFlight().getArrival()+"<br />");
		}
	%>
</div>
<div style="display:inline; border-right:solid orange 1px;padding-left:10px;padding-right:10px;">
	Return:<br/>
	< %
		if(fo_ret != null){
			out.print("from: "+fo_ret.getReferenceFlight().getDeparture()+"<br />");
			out.print("to: "+fo_ret.getReferenceFlight().getArrival()+"<br />");
		}
	%>
</div>
<div style="display:inline; border-right:solid orange 1px;padding-left:10px;padding-right:10px;">
	Stay:<br />
	< %
		if(ho_pack != null){
			out.print("Hotel: "+ho_pack.getReferenceHotel().getName()+"<br />");
			out.print("City: "+ho_pack.getReferenceHotel().getCity()+"<br />");
		}
	%>
</div>


</div>
<div style="margin-left:80%;margin-right:2px;width:20%;border-leftsolid orange 1px:">
	<form action="/cheapvacationsClient/PackageServlet" method="get">
		<button name="op" value="5" style="margin-left:10px;">Clear Selection</button>
	</form>
</div>
 -->
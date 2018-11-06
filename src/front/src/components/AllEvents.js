import React, { Component } from 'react';
import Event from './Event';
import { Container, Row, Col, Table, Button } from 'reactstrap';
import con from "../config";
import axios from "axios/index";

class AllEvents extends Component {
	constructor(props){
		super(props);
		this.state = {
			page: 1,
			list: [
				{
					id: 1,
					name: 'football',
					meetingdate: '2018/11/11',
					meetingtime: '16:16',
					location: 'pole'
				}
			]
		}
		this.join = this.join.bind(this);
	}
	join(e){
        var self = this;
        axios(con.addr+'/mainServices/event/join', {
            method: "POST",
            data: JSON.stringify({
				id: e.target.name,
                token: localStorage.getItem('token')
            }),
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(function (response) {
                console.log(response.data);
                // self.setState({list: response.data});
				e.disabled = true;
            })
            .catch(function (error) {
                console.log(error);
                // self.setState({authorized: false});
            });
	}
    componentWillMount(){
		var self = this;
        axios(con.addr+'/mainServices/event/getallevents', {
            method: "GET",
            data: JSON.stringify({
                token: localStorage.getItem('token'),
            }),
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(function (response) {
                console.log(response.data);
                self.setState({list: response.data});
            })
            .catch(function (error) {
                console.log(error);
                self.setState({authorized: false});
            });
    }
  render() {
		var self = this;
    return (
    	<div className="border rounded">
	      <Table hover>
	        <thead>
	          <tr>
	            <th>Group #</th>
	            <th>Group Name</th>
	            <th>Date</th>
	            <th>Time</th>
	            <th>Location</th>
	            <th>Action</th>
	          </tr>
	        </thead>
	        <tbody>
			{
				self.state.list.map((item, index) => {
					return (
						<tr key={item.id}>
                            <th scope="row">{index}</th>
							<td>{item.name}</td>
							<td>{item.meetingdate}</td>
							<td>{item.meetingtime}</td>
							<td>{item.location}</td>
                            <td>
                                <Button color="danger">details</Button>{' '}
                                <Button disabled={item.admin === localStorage.getItem('email')} color="success" onClick={self.join} name={item.id}>join</Button>{' '}
                            </td>
						</tr>
					)
				})
			}
	        </tbody>
	      </Table>
	      <Row className="paging">
			<Col md={{offset: 10}}>
				<Button color="primary">previous</Button>{' '}
	            <Button color="info">next</Button>{' '}
			</Col>
		  </Row>
	</div>
    );
  }
}

export default AllEvents;
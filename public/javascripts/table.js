/* 
 * @author: Ericka
 * @date: 22/02/2019
 * js for saving state of the table when listing books
 *
 */


$(document).ready(function() {
    $('#teachersTable').DataTable( {
        stateSave: true
    } );
} );

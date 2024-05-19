import {Link} from "react-router-dom";
import ShiftsSaleList from "../components/shiftsSaleList";


const ShiftSale = () => {
    return (
        <div className="h-screen p-4 pt-0">

            <div className="border-t border-gray-400 my-2"></div>

            <div className="flex-grow text-center p-10 h-80 bg-gray-200 mb-4 mt-4">
                <ShiftsSaleList />
            </div>

            <div className="flex items-center justify-center text-center p-4 h-60 bg-gray-200">
                <Link to="" className="text-blue-500 underline">
                    <p>TEST</p>
                </Link>
            </div>

        </div>


    )
}





export default ShiftSale;
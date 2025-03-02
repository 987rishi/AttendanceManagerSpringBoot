export default function ProcessingModal(){
    return(
        <div>
         {/* Animated Spinner */}
            <div className="w-12 h-12 border-4 border-blue-500 border-t-transparent rounded-full animate-spin"></div>
            <p className="mt-4 text-lg font-semibold text-gray-700">Loading...</p>
            
        </div>
    )
}